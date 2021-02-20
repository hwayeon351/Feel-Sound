import os

import subprocess

import select

import serial

import time

import json

import re

 

wpa_supplicant_conf = "/etc/wpa_supplicant/wpa_supplicant.conf"

sudo_mode = "sudo "

 

 

 

def setup():

    flag = 0

    #count = 0

    while(True):

        #count = count + 1

        time.sleep(1)

        result = os.system("ls '/dev/rfcomm0'")

        '''

        if(count > 10):

            os.system('python restart_server.py')

            os.system('ps -ef | grep servereunmi > python_output.txt')

            f = open('./python_output.txt','r')

            for i in f:

                if "root" in i:

                    if "servereunmi" in i :

                        data = i.split("      ")

                        data = data[1].split("  ")

                        print(data[0])

                        os.system('sudo kill -9 ' + data[0])

            os.system("sudo rm -rf ./servereunmi_output.txt")'''

        

        if(result!=0 and flag ==0): #no rfcomm 

            os.system('/home/pi/connect_rfcomm.sh') #run watch

            flag = 1

        elif(result == 0):
            break

    

    

def reconnection():

    #check PID of bluetooth and if there's something problem, kill -9 PID    

    os.system('ps -ef | grep watch > ps_output.txt')

    f = open('./ps_output.txt','r')

    for i in f:

        if "root" in i:

            if "rfcomm watch" in i :

                

                data = i.split(" ")[1:]

                data_str = ""

                count = 0

                for j in data:

                    data_str += j

                    if count == 1:

                        break

                    if(j==""):

                        continue

                    else:

                        count = count + 1

                        data_str = j

                    

                os.system('sudo kill -9 ' + data_str)

    os.system("sudo rm -rf ./ps_output.txt")

    os.system('sudo rm -rf /dev/rfcomm0')

    os.system('sudo service bluetooth restart')

    os.system('sudo systemctl daemon-reload')

 

    setup()

    

    

class SerialComm:

    def __init__(self):

        self.port = serial.Serial("/dev/rfcomm0", baudrate=9600, timeout=1)

 

    def read_serial(self):

        res = self.port.read(50)

        if len(res):

            return res.splitlines()

        else:

            return []

 

    def send_serial(self, text):

        self.port.write(text)

 

    def is_json(self, mJson):

        try:

            json_object = json.loads(mJson)

            if isinstance(json_object, int):

                return False

 

            if len(json_object) == 0:

                return False

        except ValueError as e:

            return False

        return True

 

    def isValidCommand(self, command, invalidCommand):

        if command not in invalidCommand:

            if re.match("^[a-zA-Z0-9. -]+$", command):

                return True

 

        return False

 

    def readExecuteSend(self, shell, ble_comm, ble_line):

 

        json_object = json.loads(ble_line)

        ip_address = ble_comm.wifi_connect(

            json_object['SSID'], json_object['PWD'])

        if ip_address == "<Not Set>":

            print("Fail to connect to Internet")

            # send back fail to configure wifi

            callback_message = {'result': "FAIL", 'IP': ip_address}

            callback_json = json.dumps(callback_message)

            ble_comm.send_serial(callback_json)

            return False

 

        else:

            #isConnected = True

            print("connect to Internet! your ip_address: " + ip_address)

            # send back configure wifi succesfully

            callback_message = {'result': "SUCCESS", 'IP': ip_address}

            callback_json = json.dumps(callback_message)

            ble_comm.send_serial(callback_json)

 

            return True

 

    def wifi_connect(self, ssid, psk):

        # write wifi config to file

        cmd = ['pirateship', 'wifi', ssid, psk]

        pirateship_wifi_output = subprocess.check_output(cmd)

        self.send_serial(pirateship_wifi_output)

 

        p = subprocess.check_output(['ifconfig', 'wlan0'])

        ip_address = "<Not Set>"

 

        for l in out.split('\n'):

            if l.strip().startswith("inet addr:"):

                ip_address = l.strip().split(' ')[1].split(':')[1]

 

        return ip_address

 

 

class ShellWrapper:

    def __init__(self):

        self.ps = subprocess.Popen(

            ['bash'],

            stdout=subprocess.PIPE,

            stderr=subprocess.PIPE,

            stdin=subprocess.PIPE)

 

    def execute_command(self, command):

        if (command == "a"):

            command = 'cat /home/pi/danger_log.txt'

        elif (command == "b"):

            command = 'cat /home/pi/normal_log.txt'

        self.ps.stdin.write(command + "\n")

 

    def get_output(self):

        timeout = False

        time_limit = .5

        lines = []

        while not timeout:

            poll_result = select.select(

                [self.ps.stdout, self.ps.stderr], [], [], time_limit)[0]

            if len(poll_result):

 

                for p in poll_result:

                    lines.append(p.readline())

            else:

                timeout = True

 

        if(len(lines)):

            return lines

        else:

            return None

 

 

def main():

    shell = ShellWrapper()

    invalidCommand = ['clear', 'sudo', 'nano', 'touch', 'vim']

    ble_comm = None

    isConnected = False

    daily_str = ""

    danger_str = ""

    while True:

        try:

            ble_comm = SerialComm()

            out = ble_comm.read_serial()

            

            try:

                daily_data = open('/home/pi/Daily_log.txt','r')

                danget_data = open('/home/pi/Danger_log.txt','r')

                daily_data_str = daily_data.readlines()[0]

                danger_data_str =  danget_data.readlines()[0]

                if(daily_data_str != daily_str):

                    daily_str = daily_data_str

                    ble_comm.send_serial(daily_data_str.encode())

                if(danger_data_str != danger_str):

                    danger_str = danger_data_str

                    ble_comm.send_serial(danger_data_str.encode())

                

            except:

                print('error')

            

            if(len(out) != 0):

                out_str = out[0].decode('utf-8')
                setting = "\n".join(out[1:])
                print(setting)

                if(out_str == 'Danger'):
                    f = open('/home/pi/Danger_setting.txt','w')
                    f.write(setting)
                    f.close()

                elif(out_str == 'Daily'):
                    f = open('/home/pi/Daily_setting.txt','w')
                    f.write(setting)
                    f.close()
                    

            '''

            for ble_line in out:

                if ble_comm.is_json(ble_line):

 

                    if not isConnected:

                        isConnected = ble_comm.readExecuteSend(

                            shell, ble_comm, ble_line)

                        break

                    else:

                        ble_comm.send_serial("Wifi has been configured")

                        break

 

                if ble_comm.isValidCommand(ble_line, invalidCommand):

                    shell.execute_command(ble_line)

                    shell_out = shell.get_output()

                    if shell_out is not None:

                        for l in shell_out:

                            print(l)

                            ble_comm.send_serial(l)

                    else:

                        ble_comm.send_serial(

                            "command '" + ble_line + "' return nothing ")

                else:

                    ble_comm.send_serial(

                        "command '" + ble_line + "' not support ")'''

 

        except serial.SerialException:

            print("waiting for connection")

            ble_comm = None

            isConnected = False

            time.sleep(1)

            setup()

 

 

if __name__ == "__main__":

    main()

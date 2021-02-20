import librosa
import numpy as np
#import RPI.gpio as GPIO
import time
from gpiozero import PWMOutputDevice
#from sklearn.cross_validation import train_test_split

import gpiozero
from keras import models
from keras import layers
from keras.models import Model
from keras.models import Sequential
from keras.layers import Dense, Dropout, Activation, Flatten
from keras.layers import Convolution2D, Conv2D, MaxPool2D, GlobalAveragePooling2D

from keras.utils import np_utils
from sklearn import metrics
import sounddevice
import soundfile as sf
from keras.models import load_model

#from gpiozero import PWMOutputDevice

import time
import neopixel
import board

#Record
f = open('/home/pi/Sound_log.txt', 'w')
f.write("")
f.close()



def s_motor():
    motor12 = PWMOutputDevice(12, active_high=True, frequency=100)
    motor16 = PWMOutputDevice(16, active_high=True, frequency=100)
    motor23 = PWMOutputDevice(23, active_high=True, frequency=100)
    motor26 = PWMOutputDevice(26, active_high=True, frequency=100)
    for i in range(7,11):
        speed = float(i/float(10))
        motor12.value = speed
        motor16.value = speed 
        motor23.value = speed 
        motor26.value = speed 
        sleep(0.5)
    motor12.off()
    motor16.off()
    motor23.off()
    motor26.off()
    
def w_motor():
    motor12 = PWMOutputDevice(12, active_high=True, frequency=100)
    motor16 = PWMOutputDevice(16, active_high=True, frequency=100)
    motor23 = PWMOutputDevice(23, active_high=True, frequency=100)
    motor26 = PWMOutputDevice(26, active_high=True, frequency=100)
    for i in range(2,5):
        speed = float(i/float(10))
        motor12.value = speed
        motor16.value = speed 
        motor23.value = speed 
        motor26.value = speed 
        sleep(0.5)
    motor12.off()
    motor16.off()
    motor23.off()
    motor26.off()

def set_mode(mode):

    if mode==11:

        for j in range(2):
            w_motor();

    elif mode ==12: 
        for j in range(2):
            s_motor();

    elif mode ==21:
        for j in range(2):
            for i in range(1,2):
                w_motor();
                time.sleep(0.8)

    elif mode ==22: 
        for j in range(2):
            for i in range(1,2):
                s_motor();
                time.sleep(0.8)
 
 

def timestamp(data):

    import time
    now = time.localtime()
    s = "%04d-%02d-%02d %02d:%02d:%02d" % (now.tm_year, now.tm_mon, now.tm_mday, now.tm_hour, now.tm_min, now.tm_sec)
    
    if (data !='None'):
        f = open('/home/pi/Sound_log.txt', 'w')
        f.write("0 "+ s + "  "+data+ "\n")
        f.close()
 

 

class_names = ['Baby Crying' ,'Car Horn', 'Dog Bark', 'Jackhammer', 'Siren', 'None']


 

def get_stream(audio, window_size): 

    if audio.shape[0] < window_size:
        padding = np.zeros(window_size-audio.shape[0])
        stream = np.concatenate((audio,padding), axis=0)
    elif audio.shape[0] >= window_size:
        stream = np.resize(audio,window_size)


    return stream

 

def windows(data, window_size):
    start = 0
    while start < len(data):
        yield int(start), int(start + window_size)
        start += (window_size / 2)

 

def extract_features():
    bands = 64
    frames = 41
    window_size = 512 * (frames - 1)
    log_specgrams = []
    #sound_clip=np.squeeze(X)
    sound_clip,s = librosa.load('/home/pi/test2.wav')
    stream = get_stream(sound_clip,window_size)
    for (start,end) in windows(sound_clip,window_size):
        if(len(sound_clip[start:end]) == window_size):
            signal = sound_clip[start:end]
            melspec = librosa.feature.melspectrogram(signal, n_mels = bands)
            logspec = librosa.amplitude_to_db(melspec)
            logspec = logspec.T.flatten()[:, np.newaxis].T
            log_specgrams.append(logspec)


    log_specgrams = np.asarray(log_specgrams).reshape(len(log_specgrams),bands,frames,1)
    features = np.concatenate((log_specgrams, np.zeros(np.shape(log_specgrams))), axis = 3)
    for i in range(len(features)):
        features[i, :, :, 1] = librosa.feature.delta(features[i, :, :, 0])

    return np.array(features)

 

def print_prediction():

    features = extract_features()
    predicted_vector = model.predict_classes(features)
    
    print("The predicted class is:", class_names[predicted_vector[0]], '\n')
    predicted_proba_vector = model.predict_proba(features)
    predicted_proba = predicted_proba_vector[0]
    for i in range(len(predicted_proba)):
        print(class_names[i], "\t\t : ", format(predicted_proba[i], '.32f') )
    
    print("\n\n")
    
    if ((predicted_vector[0]!=5) :

        # Check by bluetooth
        with open('/home/pi/Sound_setting.txt','r') as f:
            lines=f.readlines()
        numbers = []
        for line in f:
            numbers.append(line)
        target = numbers[int(predicted_vector[0]-4)]
        split_data = target.split(" ")
        a = int(split_data[0]) #sound_setting
        b = int(split_data[1]) #vibration_setting
        if(a==1):
            timestamp(class_names[predicted_vector[0]])
            set_mode(b)


duration = 2 # seconds

sample_rate=44100

model = load_model('/home/pi/contest_model.h5.)')

while(1):
    print("Record\n")
    X = sounddevice.rec(int(duration * sample_rate), samplerate=sample_rate, channels=1)

    sounddevice.wait()

    sf.write('/home/pi/test2.wav',X,sample_rate)
    print("Classification\n")
    print_prediction()

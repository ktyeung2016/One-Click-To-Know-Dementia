//
//  AppDelegate.h
//  dementia
//
//

#import <UIKit/UIKit.h>

#import <GoogleMaps/GoogleMaps.h>

#import <AVFoundation/AVFoundation.h>

@interface AppDelegate : UIResponder <UIApplicationDelegate, CLLocationManagerDelegate>

@property (strong, nonatomic) UIWindow *window;

@property (strong, nonatomic) AVAudioPlayer *audioPlayer;

@property (strong, nonatomic) NSTimer *gpsTimer;

@property (strong, nonatomic) NSUserDefaults *userDefaults;

@property (strong, nonatomic) CLLocationManager *locationManager;

- (void)setUpTimer;

@end


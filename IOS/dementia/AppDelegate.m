//
//  AppDelegate.m
//  dementia
//
//

#import "AppDelegate.h"
#import "ContactData.h"

@interface AppDelegate ()

@end

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    
    NSLog(@"%@", NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES)[0]);
    
    [GMSServices provideAPIKey:@"AIzaSyDrQNqWVeLZrIZp9_qyn1nEc_yfbNdm0Xs"];
    
    [[UINavigationBar appearance] setBackgroundImage:[UIImage imageNamed:@"Background_NavigationBar"] forBarMetrics:UIBarMetricsDefault];
    [[UINavigationBar appearance] setTitleTextAttributes:@{NSForegroundColorAttributeName: [UIColor whiteColor]}];
    
    self.userDefaults = [NSUserDefaults standardUserDefaults];
    
    self.locationManager = [[CLLocationManager alloc] init];
    [self.locationManager setDelegate:self];
    [self.locationManager setDistanceFilter:kCLDistanceFilterNone];
    [self.locationManager setDesiredAccuracy:kCLLocationAccuracyBest];
    
    if ([self.locationManager respondsToSelector:@selector(requestAlwaysAuthorization)]) {
        [self.locationManager requestAlwaysAuthorization] ;
        [self.locationManager requestWhenInUseAuthorization];
    }
    
    [self.locationManager startUpdatingLocation];
    
    [self setUpAudioPlayer];
    [self setUpUserDefaults];
    [self setUpTimer];

    return YES;
}

- (void)applicationWillResignActive:(UIApplication *)application {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
}

- (void)applicationWillTerminate:(UIApplication *)application {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}

- (void)setUpUserDefaults {
    
    if ([self.userDefaults objectForKey:@"userSetting:gameMode"] == nil) {
        [self.userDefaults setObject:@"Full"forKey:@"userSetting:gameMode"];
        [self.userDefaults synchronize];
        
        [[JDDataManager sharedInstance] setGameMode:@"Full"];
    }
    else {
        [[JDDataManager sharedInstance] setGameMode:[self.userDefaults objectForKey:@"userSetting:gameMode"]];
    }
    
    if ([self.userDefaults objectForKey:@"userSetting:sound"] == nil) {
        [self.userDefaults setBool:NO forKey:@"userSetting:sound"];
        [self.userDefaults synchronize];
    }
    
    if ([self.userDefaults objectForKey:@"userSetting:language"] == nil) {
        [self.userDefaults setObject:@"ZH" forKey:@"userSetting:language"];
        [self.userDefaults synchronize];
        
        [[HMLocalization sharedInstance] setLanguage:@"ZH"];
    }
    else {
        [[HMLocalization sharedInstance] setLanguage:[self.userDefaults objectForKey:@"userSetting:language"]];
    }
    
    if ([self.userDefaults objectForKey:@"userSetting:fontSize"] == nil) {
        [self.userDefaults setObject:@"Large" forKey:@"userSetting:fontSize"];
        [self.userDefaults synchronize];
        
        [[JDDataManager sharedInstance] setFontSize:@"Large"];
    }
    else {
        [[JDDataManager sharedInstance] setFontSize:[self.userDefaults objectForKey:@"userSetting:fontSize"]];
    }
    
    if ([self.userDefaults objectForKey:@"userSetting:gpsTime"] == nil) {
        [self.userDefaults setObject:@60 forKey:@"userSetting:gpsTime"];
        [self.userDefaults synchronize];
    }
    
    if ([self.userDefaults objectForKey:@"userSetting:autoUpdate"] == nil) {
        [self.userDefaults setBool:YES forKey:@"userSetting:autoUpdate"];
        [self.userDefaults synchronize];
    }
    
    if ([self.userDefaults objectForKey:@"termsShown"] == nil) {
        [self.userDefaults setBool:NO forKey:@"termsShown"];
        [self.userDefaults synchronize];
    }
    
    /*
     */
    if ([[self.userDefaults objectForKey:@"userSetting:sound"] boolValue]) {
        [self setAudioPlayerPlay];
    }
}

- (void)setUpAudioPlayer {
    NSURL *audioFileLocationURL = [[NSBundle mainBundle] URLForResource:@"JCCPAbgm" withExtension:@"mp3"];
    
    NSError *error;
    self.audioPlayer = [[AVAudioPlayer alloc] initWithContentsOfURL:audioFileLocationURL error:&error];
    [self.audioPlayer setNumberOfLoops:-1];
    
    if (error) {
        NSLog(@"%@", [error localizedDescription]);
    }
    else {
        [[AVAudioSession sharedInstance] setCategory:AVAudioSessionCategoryPlayback error:nil];
        [[AVAudioSession sharedInstance] setActive: YES error: nil];
        
        [self.audioPlayer prepareToPlay];
    }
}

- (void)setAudioPlayerPlay {
    [self.audioPlayer play];
}

- (void)setUpTimer {
    [self.gpsTimer invalidate];
    self.gpsTimer = nil;
    
    self.gpsTimer = [NSTimer scheduledTimerWithTimeInterval:[[self.userDefaults objectForKey:@"userSetting:gpsTime"] integerValue] * 60 target:self selector:@selector(sendGPSToServer) userInfo:nil repeats:YES];
}

#pragma mark - NSTimer handling

- (void)sendGPSToServer {
    if ([self.userDefaults objectForKey:@"userInfo"][@"id"] != nil &&
        [CLLocationManager locationServicesEnabled] &&
        [CLLocationManager authorizationStatus] == kCLAuthorizationStatusAuthorized) {
        
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc] init];
        [dateFormatter setDateFormat:@"EEE MMM d HH:mm:ss ZZZ yyyy"];
        
        NSMutableString *family = [[NSMutableString alloc] init];
        FMDatabase *db = [[[JDDataManager sharedInstance] dbHepler] openDB];
        [db open];
        
        [family appendString:@"["];
        bool addedFamily = FALSE;
        for (NSDictionary *tempDict in [[[JDDataManager sharedInstance] dbHepler]
                                        getDataFromTableWithQuery:[db executeQuery:@"SELECT * FROM `contact` WHERE `create_user` = ?", [self.userDefaults objectForKey:@"userInfo"][@"id"]]
                                        keys:ContactTableAttributes]) {
            
            ContactData *data = [[ContactData alloc] initWithData:tempDict];
            if (addedFamily) {
                [family appendString:@","];
            }
            [family appendFormat:@"{\"name\":\"%@\", \"email\":\"%@\"}", data.memberName, data.memberEmail];
            addedFamily = TRUE;
        }
        [family appendString:@"]"];
        
        [db close];
        
        NSDictionary *tempDict = @{@"position": [NSString stringWithFormat:@"%.8f:%.8f:%@",
                                                 self.locationManager.location.coordinate.longitude,
                                                 self.locationManager.location.coordinate.latitude,
                                                 [dateFormatter stringFromDate:[NSDate date]]],
                                   @"user_id": [self.userDefaults objectForKey:@"userInfo"][@"id"],
                                   @"family": family};
        
        [[JDDataManager sharedInstance] getJSONWithAPI:@"update_position_without_token" parameter:tempDict result:^(NSDictionary *result) {
            
        } errorMsg:^(NSString *errorMsg) {
            
        }];
    }
}

@end

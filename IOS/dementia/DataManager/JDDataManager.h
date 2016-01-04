//
//  JDDataManager.h
//  dementia
//
//

#import <Foundation/Foundation.h>

#import "AFHTTPRequestOperationManager.h"

#import "JDDBHelper.h"

@interface JDDataManager : NSObject

@property (strong, nonatomic) AFHTTPRequestOperationManager *operation;

@property (strong, nonatomic) JDDBHelper *dbHepler;

@property (strong, nonatomic) NSString *gameMode;

@property (strong, nonatomic) NSString *fontSize;

@property (strong, nonatomic) NSString *accessToken;

+ (instancetype)sharedInstance;

- (NSString *)returnGameMode;

- (NSString *)returnFontSize;

- (NSString *)returnAccessToken;

@end

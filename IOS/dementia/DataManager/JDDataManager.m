//
//  JDDataManager.m
//  dementia
//
//

#import "JDDataManager.h"

@implementation JDDataManager

+ (instancetype)sharedInstance {
    static id sharedInstance = nil;
    static dispatch_once_t onceToken = 0;
    dispatch_once(&onceToken, ^{
        sharedInstance = [[self alloc] initWithManager:[AFHTTPRequestOperationManager manager]];
    });
    return sharedInstance;
}

- (instancetype)initWithManager:(AFHTTPRequestOperationManager *)manager {
    if (self) {
        self.operation = manager;
        self.operation.responseSerializer = [AFHTTPResponseSerializer serializer];
        
        self.dbHepler = [[JDDBHelper alloc] init];
    }
    return self;
}

- (NSString *)returnGameMode {
    return (self.gameMode == nil) ? @"" : self.gameMode;
}

- (NSString *)returnFontSize {
    return (self.fontSize == nil) ? @"" : self.fontSize;
}

- (NSString *)returnAccessToken {
    return (self.accessToken == nil) ? @"" : self.accessToken;
}

@end

//
//  JDDataManager+API.h
//  dementia
//
//

#import "JDDataManager.h"

#define API_URL @"http://dementia.westcomzivo.com/dementia/api/"
#define MEDIA_URL @"http://dementia.westcomzivo.com/dementia/upload/media/"

@interface JDDataManager (API)

- (void)getJSONWithAPI:(NSString *)api parameter:(NSDictionary *)parameter result:(void (^)(NSDictionary *))result errorMsg:(void (^)(NSString *))errorMsg;

- (void)getMediaWithMediaName:(NSString *)name result:(void (^)(UIImage *))result errorMsg:(void (^)(NSString *))errorMsg;

@end

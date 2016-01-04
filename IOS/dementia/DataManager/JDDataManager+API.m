//
//  JDDataManager+API.m
//  dementia
//
//

#import "JDDataManager+API.h"

@implementation JDDataManager (API)

- (void)getJSONWithAPI:(NSString *)api parameter:(NSDictionary *)parameter result:(void (^)(NSDictionary *))result errorMsg:(void (^)(NSString *))errorMsg {
    NSString *urlStr = [NSString stringWithFormat:@"%@%@.php", API_URL, api];
    
    NSMutableDictionary *newParameter = [[NSMutableDictionary alloc] initWithDictionary:parameter];
    if (![api isEqualToString:@"auth"]) {
        [newParameter setObject:[self returnAccessToken] forKey:@"token"];
    }
    
    [self.operation GET:urlStr parameters:newParameter success:^(AFHTTPRequestOperation *operation, id responseObject) {
        NSError *error = nil;
        NSDictionary *jsonDict = [NSJSONSerialization JSONObjectWithData:responseObject options:kNilOptions error:&error];
        
        result(jsonDict);
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        if ([[[error userInfo] objectForKey:@"NSLocalizedDescription"] isEqualToString:@"The Internet connection appears to be offline."]) {
            errorMsg(@"NETWORK_ERROR");
        }
        else {
            errorMsg(@"ERROR");
        }
        
        NSLog(@"Request Failure: %@",[error userInfo]);
    }];
}

- (void)getMediaWithMediaName:(NSString *)name result:(void (^)(UIImage *))result errorMsg:(void (^)(NSString *))errorMsg {
    NSString *urlStr = [NSString stringWithFormat:@"%@%@", MEDIA_URL, name];
    
    [self.operation GET:urlStr parameters:nil success:^(AFHTTPRequestOperation *operation, id responseObject) {
        result([UIImage imageWithData:responseObject]);
    } failure:^(AFHTTPRequestOperation *operation, NSError *error) {
        errorMsg(@"ERROR");
        
        NSLog(@"Request Failure: %@",[error userInfo]);
    }];
}

@end

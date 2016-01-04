//
//  GameRankingData.h
//  dementia
//
//

#import <Foundation/Foundation.h>

@interface GameRankingData : NSObject

@property (strong, nonatomic) NSString *ranking;
@property (strong, nonatomic) NSString *score;
@property (strong, nonatomic) NSString *date;

- (id)initWithData:(NSDictionary *)dict;

@end

//
//  GameRankingData.m
//  dementia
//
//

#import "GameRankingData.h"

@implementation GameRankingData

- (id)initWithData:(NSDictionary *)dict {
    self = [super init];
    if (self) {
        self.ranking = dict[@"ranking"];
        self.score = [NSString stringWithFormat:@"%ld", (long)[dict[@"score"] integerValue]];
        
        NSDate *tempDate = [NSDate dateWithTimeIntervalSince1970:[dict[@"date"] doubleValue]];
        NSDateFormatter *dateFormatter = [[NSDateFormatter alloc]init];
        [dateFormatter setDateFormat:@"dd-MM-yyyy"];
        
        self.date = [dateFormatter stringFromDate:tempDate];
    }
    return self;
}

@end

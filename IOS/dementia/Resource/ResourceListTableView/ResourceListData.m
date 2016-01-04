//
//  ResourceListData.m
//  dementia
//
//

#import "ResourceListData.h"

@implementation ResourceListData

- (id)initWithData:(NSString *)data {
    self = [super init];
    if (self) {
        self.resourceTitle = data;
    }
    return self;
}

@end

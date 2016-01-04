//
//  KnowledgeData.m
//  dementia
//
//

#import "KnowledgeData.h"

@implementation KnowledgeData

- (id)initWithData:(NSString *)data {
    self = [super init];
    if (self) {
        self.knowledgeTitle = data;
    }
    return self;
}

@end

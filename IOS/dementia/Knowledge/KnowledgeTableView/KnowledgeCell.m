//
//  KnowledgeCell.m
//  dementia
//
//

#import "KnowledgeCell.h"

@implementation KnowledgeCell

- (void)setUpViewWithData:(KnowledgeData *)data {
    [self.titleLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.titleLbl setText:data.knowledgeTitle];
    [self.btnKnowledgeItem setIsAccessibilityElement:TRUE];
    [self.btnKnowledgeItem setAccessibilityHint:[@"前往" stringByAppendingString:data.knowledgeTitle]];
}

@end

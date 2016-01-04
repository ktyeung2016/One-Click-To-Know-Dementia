//
//  ResourceListCell.m
//  dementia
//
//

#import "ResourceListCell.h"

@implementation ResourceListCell

- (void)setUpViewWithData:(ResourceListData *)data {
    [self.titleLbl setFont:[UIFont systemFontOfSize:FontSizeLarge(19.0f)]];
    [self.titleLbl setText:data.resourceTitle];
    [self.btnResourceItem setIsAccessibilityElement:TRUE];
    [self.btnResourceItem setAccessibilityHint:[@"前往" stringByAppendingString:data.resourceTitle]];
}

@end

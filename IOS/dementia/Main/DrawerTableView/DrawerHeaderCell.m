//
//  DrawerHeaderCell.m
//  dementia
//
//

#import "DrawerHeaderCell.h"

@implementation DrawerHeaderCell

- (void)awakeFromNib {
    [self setUpLocalizationView];
}

- (void)setUpLocalizationView {
    [self.myLocationMark setText:LocalizedString(@"MAIN_CATEGORY_MY_LOCATION")];
}

@end

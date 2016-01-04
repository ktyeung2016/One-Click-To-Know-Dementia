//
//  DrawerButtonCell.m
//  dementia
//
//

#import "DrawerButtonCell.h"

@implementation DrawerButtonCell

- (void)setUpViewWithData:(DrawerData *)data {
    [self.backgroundImgView setImage:data.backgroundImage];
    [self.itemLbl setText:data.itemName];
}

@end

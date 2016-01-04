//
//  GameRememberBoxCell.m
//  dementia
//
//

#import "GameRememberBoxCell.h"

@implementation GameRememberBoxCell

- (void)setUpViewWithBoxShow:(BOOL)boxShow {
    [self.elementImgView setImage:(boxShow) ? [UIImage imageNamed:@"Game4_Element_Square"] : nil];
}

- (void)updateSelection {
    [self.elementImgView setImage:(self.elementImgView.image) ? nil : [UIImage imageNamed:@"Game4_Element_Square"]];
}

@end

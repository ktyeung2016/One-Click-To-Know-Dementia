//
//  GameRememberRectangleCell.m
//  dementia
//
//

#import "GameRememberRectangleCell.h"

@implementation GameRememberRectangleCell

- (void)setUpViewWithBlueBoxShow:(BOOL)blueBoxShow blackBoxShow:(BOOL)blackBoxShow {
    [self.elementImgView setBackgroundColor:[UIColor clearColor]];
    
    if (blueBoxShow) {
        [self.elementImgView setImage:[UIImage imageNamed:@"Game4_Element_Rectangle"]];
    }
    else if (blackBoxShow) {
        [self.elementImgView setImage:nil];
        [self.elementImgView setBackgroundColor:[UIColor blackColor]];
    }
    else {
        [self.elementImgView setImage:nil];
    }
}

- (void)updateSelection {
    [self.elementImgView setImage:(self.elementImgView.image) ? nil : [UIImage imageNamed:@"Game4_Element_Rectangle"]];
}

@end

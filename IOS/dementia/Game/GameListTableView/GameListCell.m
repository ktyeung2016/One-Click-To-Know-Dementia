//
//  GameListCell.m
//  dementia
//
//

#import "GameListCell.h"

@implementation GameListCell

- (void)setUpViewWithImageName:(NSString *)imageName gameName:(NSString *)gameName {
    [self.backgroundImgView setImage:[UIImage imageNamed:imageName]];
    [self.backgroundImgView setIsAccessibilityElement:TRUE];
    [self.backgroundImgView setAccessibilityLabel:@""];
    [self.backgroundImgView setAccessibilityHint:gameName];
    [self.gameNameLbl setText:gameName];
    [self.gameNameLbl setFont:[UIFont boldSystemFontOfSize:FontSizeLarge(23.0f)]];
}

@end

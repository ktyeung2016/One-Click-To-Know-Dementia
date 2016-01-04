//
//  GameBuyerThingRectangleRightCell.m
//  dementia
//
//

#import "GameBuyerThingRectangleRightCell.h"

@implementation GameBuyerThingRectangleRightCell

- (void)setUpViewWithImageNumber:(NSUInteger)imageNumber {
    [self.thingImgView setTag:imageNumber];
    
    [self.thingImgView setImage:[UIImage imageNamed:[NSString stringWithFormat:(imageNumber < 10) ? @"Game1_a00%lu" : @"Game1_a0%lu", (unsigned long)imageNumber]]];
    
    [self.outlineImgView setImage:[UIImage imageNamed:@"Game1_Outline"]];
}

- (void)setUpTipsViewWithImageNumber:(NSUInteger)imageNumber answered:(BOOL)answered {
    [self.thingImgView setImage:[UIImage imageNamed:[NSString stringWithFormat:(imageNumber < 10) ? @"Game1_a00%lu" : @"Game1_a0%lu", (unsigned long)imageNumber]]];
    
    [self.outlineImgView setImage:[UIImage imageNamed:(answered) ? @"Game1_Outline_Tick" : @"Game1_Outline"]];
}

- (void)updateOutlineImage:(BOOL)isCorrect {
    [self.outlineImgView setImage:[UIImage imageNamed:(isCorrect) ? @"Game1_Outline_Tick" : @"Game1_Outline_Cross"]];
}

@end

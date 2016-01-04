//
//  GameBuyerThingRectangleRightCell.h
//  dementia
//
//

#import <UIKit/UIKit.h>

@interface GameBuyerThingRectangleRightCell : UICollectionViewCell

@property (weak, nonatomic) IBOutlet UIImageView *outlineImgView;
@property (weak, nonatomic) IBOutlet UIImageView *thingImgView;

- (void)setUpViewWithImageNumber:(NSUInteger)imageNumber;

- (void)setUpTipsViewWithImageNumber:(NSUInteger)imageNumber answered:(BOOL)answered;

- (void)updateOutlineImage:(BOOL)isCorrect;

@end

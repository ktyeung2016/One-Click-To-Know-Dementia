//
//  GameRememberRectangleCell.h
//  dementia
//
//

#import <UIKit/UIKit.h>

@interface GameRememberRectangleCell : UICollectionViewCell

@property (weak, nonatomic) IBOutlet UIImageView *elementImgView;

- (void)setUpViewWithBlueBoxShow:(BOOL)blueBoxShow blackBoxShow:(BOOL)blackBoxShow;

- (void)updateSelection;

@end

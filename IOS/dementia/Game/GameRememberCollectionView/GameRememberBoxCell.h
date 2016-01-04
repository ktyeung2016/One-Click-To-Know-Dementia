//
//  GameRememberBoxCell.h
//  dementia
//
//

#import <UIKit/UIKit.h>

@interface GameRememberBoxCell : UICollectionViewCell

@property (weak, nonatomic) IBOutlet UIImageView *elementImgView;

- (void)setUpViewWithBoxShow:(BOOL)boxShow;

- (void)updateSelection;

@end

//
//  GameCutShadowViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface GameCutShadowViewController : BaseViewController <UIGestureRecognizerDelegate>

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;
@property (weak, nonatomic) IBOutlet UIButton *confirmBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UILabel *questionNumberLbl;
@property (weak, nonatomic) IBOutlet UIView *shadowView;
@property (weak, nonatomic) IBOutlet UIScrollView *scrollPuzzle;
@property (weak, nonatomic) IBOutlet UIImageView *tickImg;

@property (weak, nonatomic) IBOutlet UIButton *questionBtn;
@property (strong, nonatomic) IBOutletCollection(UIImageView) NSArray *heartImg;

- (IBAction)confirmBtnClick:(id)sender;
- (IBAction)informationBtnClick:(id)sender;
- (IBAction)questionBtnClick:(id)sender;

@end

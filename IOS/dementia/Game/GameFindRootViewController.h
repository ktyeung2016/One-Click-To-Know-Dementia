//
//  GameFindRootViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface GameFindRootViewController : BaseViewController

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;

/* Variable */
@property (weak, nonatomic) IBOutlet UILabel *questionNumberLbl;
@property (weak, nonatomic) IBOutlet UIImageView *questionImgView;
@property (weak, nonatomic) IBOutlet UIImageView *tickImg;
@property (strong, nonatomic) IBOutletCollection(UIButton) NSArray *answerBtn;

@property (weak, nonatomic) IBOutlet UIButton *informationBtn;
@property (weak, nonatomic) IBOutlet UIButton *questionBtn;
@property (strong, nonatomic) IBOutletCollection(UIImageView) NSArray *heartImg;

- (IBAction)answerBtnClick:(id)sender;
- (IBAction)informationBtnClick:(id)sender;
- (IBAction)questionBtnClick:(id)sender;

@end

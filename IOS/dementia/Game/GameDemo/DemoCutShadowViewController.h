//
//  DemoCutShadowViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface DemoCutShadowViewController : BaseViewController

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *playMethodMark;
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;
@property (weak, nonatomic) IBOutlet UIButton *confirmBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UIView *topView;
@property (weak, nonatomic) IBOutlet UIView *layerView;

@property (weak, nonatomic) IBOutlet UILabel *pagesLbl;

@property (weak, nonatomic) IBOutlet UIImageView *starShadowImg;
@property (weak, nonatomic) IBOutlet UIImageView *circleShadowImg;
@property (weak, nonatomic) IBOutlet UIImageView *crossShadowImg;

@property (weak, nonatomic) IBOutlet UIImageView *starImg;
@property (weak, nonatomic) IBOutlet UIImageView *circleImg;
@property (weak, nonatomic) IBOutlet UIImageView *crossImg;
@property (weak, nonatomic) IBOutlet UIImageView *crossImg2;

@property (weak, nonatomic) IBOutlet UIImageView *tickImg;
@property (weak, nonatomic) IBOutlet UIImageView *heartImg;

@property (weak, nonatomic) IBOutlet UIView *demoLayerMask;
@property (weak, nonatomic) IBOutlet UIView *middleDemoView;
@property (weak, nonatomic) IBOutlet UILabel *middleDemoLbl;

@property (weak, nonatomic) IBOutlet UIView *bottomDemoView;
@property (weak, nonatomic) IBOutlet UILabel *bottomDemoLbl;

@property (copy, nonatomic) void(^popFromDemoBlock)(BOOL status);

- (IBAction)backBtnClick:(id)sender;
- (IBAction)nextBtnClick:(id)sender;

@end

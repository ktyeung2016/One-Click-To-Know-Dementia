//
//  DemoBuyerViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface DemoBuyerViewController : BaseViewController

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *playMethodMark;

@property (weak, nonatomic) IBOutlet UIButton *confirmBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UIView *topView;
@property (weak, nonatomic) IBOutlet UIView *layerView;

@property (weak, nonatomic) IBOutlet UILabel *pagesLbl;
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;

@property (weak, nonatomic) IBOutlet UIView *memorizeView;
@property (weak, nonatomic) IBOutlet UIImageView *memorizeFirstOutline;

@property (weak, nonatomic) IBOutlet UIView *dayView;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *dayLbl;

@property (weak, nonatomic) IBOutlet UIView *thingsView;
@property (weak, nonatomic) IBOutlet UIImageView *thingsFirstOutline;
@property (weak, nonatomic) IBOutlet UIImageView *thingsSecondOutline;

@property (weak, nonatomic) IBOutlet UIView *tipsView;

@property (weak, nonatomic) IBOutlet UIView *demoLayerMask;
@property (weak, nonatomic) IBOutlet UIView *bottomDemoView;
@property (weak, nonatomic) IBOutlet UILabel *bottomDemoLbl;

@property (copy, nonatomic) void(^popFromDemoBlock)(BOOL status);

- (IBAction)backBtnClick:(id)sender;
- (IBAction)nextBtnClick:(id)sender;

@end

//
//  DemoCalculationViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface DemoCalculationViewController : BaseViewController

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *playMethodMark;

@property (weak, nonatomic) IBOutlet UIButton *startBtn;

@property (weak, nonatomic) IBOutlet UILabel *itemsCostViewItemsMark;
@property (weak, nonatomic) IBOutlet UILabel *itemsCostViewPriceMark;
@property (weak, nonatomic) IBOutlet UILabel *itemsCostViewQuantityMark;
@property (weak, nonatomic) IBOutlet UILabel *itemsCostViewTotalMark;
@property (weak, nonatomic) IBOutlet UIButton *itemsCostViewConfirmBtn;

@property (weak, nonatomic) IBOutlet UILabel *leftCostViewTotalPriceMark;
@property (weak, nonatomic) IBOutlet UILabel *leftCostMark;
@property (weak, nonatomic) IBOutlet UIButton *leftCostViewConfirmBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UIView *topView;
@property (weak, nonatomic) IBOutlet UIView *layerView;

@property (weak, nonatomic) IBOutlet UILabel *pagesLbl;

@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;

@property (weak, nonatomic) IBOutlet UIView *memorizeMoneyView;
@property (weak, nonatomic) IBOutlet UILabel *memorizeMoneyLbl;

@property (weak, nonatomic) IBOutlet UIView *calculateItemsCostView;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *itemsPriceLbl;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *itemsQuantityLbl;
@property (weak, nonatomic) IBOutlet UITextField *itemsCostTxtField;

@property (weak, nonatomic) IBOutlet UIView *calculateLeftCostView;
@property (weak, nonatomic) IBOutlet UITextField *leftCostTxtField;

@property (weak, nonatomic) IBOutlet UIView *demoLayerMask;
@property (weak, nonatomic) IBOutlet UIView *middleDemoView;
@property (weak, nonatomic) IBOutlet UIButton *middleDemoBtn;

@property (weak, nonatomic) IBOutlet UIView *bottomDemoView;
@property (weak, nonatomic) IBOutlet UILabel *bottomDemoLbl;

@property (copy, nonatomic) void(^popFromDemoBlock)(BOOL status);

- (IBAction)backBtnClick:(id)sender;
- (IBAction)nextBtnClick:(id)sender;

@end

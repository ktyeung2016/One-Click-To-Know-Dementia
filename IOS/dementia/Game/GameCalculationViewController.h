//
//  GameCalculationViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface GameCalculationViewController : BaseViewController

/* For Localization */
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
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;

@property (weak, nonatomic) IBOutlet UILabel *questionNumberLbl;

@property (weak, nonatomic) IBOutlet UIView *memorizeMoneyView;
@property (weak, nonatomic) IBOutlet UILabel *memorizeMoneyLbl;

@property (weak, nonatomic) IBOutlet UIView *calculateItemsCostView;
@property (weak, nonatomic) IBOutlet UIView *thirdItemView;
@property (weak, nonatomic) IBOutlet UIImageView *thirdOutlineImgView;
@property (strong, nonatomic) IBOutletCollection(UIImageView) NSArray *itemsPhotoImgView;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *itemsPriceLbl;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *itemsQuantityLbl;
@property (weak, nonatomic) IBOutlet UITextField *itemsCostTxtField;

@property (weak, nonatomic) IBOutlet UIView *calculateLeftCostView;
@property (weak, nonatomic) IBOutlet UILabel *leftCostLbl;
@property (weak, nonatomic) IBOutlet UITextField *leftCostTxtField;

@property (weak, nonatomic) IBOutlet UIButton *questionBtn;
@property (strong, nonatomic) IBOutletCollection(UIImageView) NSArray *heartImg;

- (IBAction)startBtnClick:(id)sender;

- (IBAction)itemsCostViewConfirmBtnClick:(id)sender;

- (IBAction)leftCostViewConfirmBtnClick:(id)sender;

- (IBAction)informationBtnClick:(id)sender;
- (IBAction)questionBtnClick:(id)sender;

@end

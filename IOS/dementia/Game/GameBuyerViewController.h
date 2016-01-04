//
//  GameBuyerViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface GameBuyerViewController : BaseViewController <UICollectionViewDataSource, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout>

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *tipsTitleLbl;

@property (weak, nonatomic) IBOutlet UIButton *confirmBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;

@property (weak, nonatomic) IBOutlet UILabel *questionNumberLbl;
@property (strong, nonatomic) IBOutletCollection(UIImageView) NSArray *starImg;

@property (weak, nonatomic) IBOutlet UICollectionView *thingsCollectionView;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *thingsCollectionViewHeightConstraint;

@property (weak, nonatomic) IBOutlet UIView *dayView;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *dayLbl;

@property (weak, nonatomic) IBOutlet UIButton *questionBtn;
@property (strong, nonatomic) IBOutletCollection(UIImageView) NSArray *heartImg;

@property (weak, nonatomic) IBOutlet UIView *tipsView;

- (IBAction)dayBtnClick:(id)sender;

- (IBAction)confirmBtnClick:(id)sender;

- (IBAction)informationBtnClick:(id)sender;
- (IBAction)questionBtnClick:(id)sender;

@end

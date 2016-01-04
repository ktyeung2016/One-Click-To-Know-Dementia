//
//  DemoRememberViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface DemoRememberViewController : BaseViewController <UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout>

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *playMethodMark;
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;

/* Variable */
@property (weak, nonatomic) IBOutlet UIView *topView;
@property (weak, nonatomic) IBOutlet UIView *layerView;

@property (weak, nonatomic) IBOutlet UILabel *pagesLbl;

@property (weak, nonatomic) IBOutlet UIImageView *tickImg;
@property (weak, nonatomic) IBOutlet UICollectionView *boxCollectionView;
@property (weak, nonatomic) IBOutlet UIButton *startAndConfirmBtn;

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

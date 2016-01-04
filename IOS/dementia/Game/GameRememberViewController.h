//
//  GameRememberViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface GameRememberViewController : BaseViewController <UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout>

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;

/* Variable */
@property (weak, nonatomic) IBOutlet UILabel *questionNumberLbl;
@property (weak, nonatomic) IBOutlet UIImageView *backgroundImgView;
@property (weak, nonatomic) IBOutlet UIImageView *tickImg;
@property (weak, nonatomic) IBOutlet UICollectionView *boxCollectionView;
@property (weak, nonatomic) IBOutlet UIButton *startAndConfirmBtn;

@property (weak, nonatomic) IBOutlet UIButton *refreshBtn;
@property (weak, nonatomic) IBOutlet UIButton *questionBtn;
@property (strong, nonatomic) IBOutletCollection(UIImageView) NSArray *heartImg;

- (IBAction)startAndConfirmBtnClick:(id)sender;
- (IBAction)refreshBtnClick:(id)sender;
- (IBAction)questionBtnClick:(id)sender;

@end

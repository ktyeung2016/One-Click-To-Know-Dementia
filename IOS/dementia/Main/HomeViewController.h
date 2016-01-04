//
//  HomeViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface HomeViewController : BaseViewController <UIAlertViewDelegate>

@property (weak, nonatomic) IBOutlet UIView *fullView;
@property (weak, nonatomic) IBOutlet UIView *gameView;

@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *fullCategoryNameLbl;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *gameCategoryNameLbl;

@property (weak, nonatomic) IBOutlet UIImageView *bannerImgView;
@property (weak, nonatomic) IBOutlet UIButton *btnDirectFamilyContact;
@property (weak, nonatomic) IBOutlet UILabel *lblDirectFamilyContact;

- (IBAction)categoryBtnClick:(id)sender;

- (void)pushControllerWithStoryboardName:(NSString *)storyboardName identifier:(NSString *)identifier animated:(BOOL)animated;

@end

//
//  ResourceDetailViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface ResourceDetailViewController : BaseViewController

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *districtMark;
@property (weak, nonatomic) IBOutlet UILabel *targetMark;
@property (weak, nonatomic) IBOutlet UILabel *phoneMark;
@property (weak, nonatomic) IBOutlet UILabel *websiteMark;
@property (weak, nonatomic) IBOutlet UILabel *remarkMark;
@property (weak, nonatomic) IBOutlet UIButton *websiteBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UIScrollView *scrollBackground;

@property (weak, nonatomic) IBOutlet UILabel *organizationLbl;
@property (weak, nonatomic) IBOutlet UILabel *serviceLbl;
@property (weak, nonatomic) IBOutlet UILabel *districtLbl;
@property (weak, nonatomic) IBOutlet UILabel *phoneLbl;
@property (weak, nonatomic) IBOutlet UILabel *remarkLbl;

@property (weak, nonatomic) IBOutlet UIView *contentView;
@property (weak, nonatomic) IBOutlet UIView *targetView;

/* Constraints */
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *organizationLblHeightConstraint;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *serviceLblHeightConstraint;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *contentViewHeightConstraint;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *districtLblHeightConstraint;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *targetViewHeightConstraint;
@property (weak, nonatomic) IBOutlet NSLayoutConstraint *remarkLblHeightConstraint;

@property (strong, nonatomic) NSDictionary *resourceDetailDict;

- (IBAction)phoneBtnClick:(id)sender;
- (IBAction)websiteBtnClick:(id)sender;

@end

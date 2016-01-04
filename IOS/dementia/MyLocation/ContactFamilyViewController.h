//
//  ContactFamilyViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface ContactFamilyViewController : BaseViewController <UITableViewDataSource, UITableViewDelegate>

/* For Localization */
@property (weak, nonatomic) IBOutlet UIButton *addMemberBtn;
@property (weak, nonatomic) IBOutlet UILabel *emergencyHotlineMark;

@property (weak, nonatomic) IBOutlet UILabel *useMethodMark;
@property (weak, nonatomic) IBOutlet UILabel *demoLbl;

/* Variable */
@property (weak, nonatomic) IBOutlet UITableView *contactTable;
@property (weak, nonatomic) IBOutlet UILabel *emergencyHotlineLbl;

@property (weak, nonatomic) IBOutlet UILabel *pagesLbl;

@property (weak, nonatomic) IBOutlet UIView *demoTopView;
@property (weak, nonatomic) IBOutlet UIView *demoLayerView;
@property (weak, nonatomic) IBOutlet UIView *demoLayerMask;

@property (weak, nonatomic) IBOutlet NSLayoutConstraint *demoTopViewHeightConstraint;

@property (assign, nonatomic) BOOL isModeDemo;
@property (assign, nonatomic) NSUInteger currentPageNumber;

- (IBAction)backBtnClick:(id)sender;
- (IBAction)nextBtnClick:(id)sender;

- (IBAction)addMemberBtnClick:(id)sender;
- (IBAction)editMemberBtnClick:(id)sender;
- (IBAction)callMemberBtnClick:(id)sender;
- (IBAction)callHotlineBtnClick:(id)sender;
- (IBAction)call999BtnClick:(id)sender;
- (IBAction)call23432255BtnClick:(id)sender;
- (IBAction)call23388312BtnClick:(id)sender;
- (IBAction)call23820881BtnClick:(id)sender;
- (IBAction)call27876865BtnClick:(id)sender;
- (IBAction)call18288BtnClick:(id)sender;

@end

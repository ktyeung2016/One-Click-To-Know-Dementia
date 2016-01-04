//
//  SettingViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface SettingViewController : BaseViewController <UIAlertViewDelegate>

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;
@property (weak, nonatomic) IBOutlet UILabel *modeMark;
@property (weak, nonatomic) IBOutlet UILabel *modeFullMark;
@property (weak, nonatomic) IBOutlet UILabel *modeGameMark;
@property (weak, nonatomic) IBOutlet UILabel *languageMark;
@property (weak, nonatomic) IBOutlet UILabel *languageZhMark;
@property (weak, nonatomic) IBOutlet UILabel *languageGbMark;
@property (weak, nonatomic) IBOutlet UILabel *fontSizeMark;
@property (weak, nonatomic) IBOutlet UILabel *fontSizeLargeMark;
@property (weak, nonatomic) IBOutlet UILabel *fontSizeSmallMark;
@property (weak, nonatomic) IBOutlet UILabel *soundMark;
@property (weak, nonatomic) IBOutlet UILabel *gpsMark;
@property (weak, nonatomic) IBOutlet UILabel *gpsTimeMark;
@property (weak, nonatomic) IBOutlet UILabel *autoUpdateMark;
@property (weak, nonatomic) IBOutlet UIButton *clearFamilyDataBtn;
@property (weak, nonatomic) IBOutlet UIButton *clearRankingDataBtn;
@property (weak, nonatomic) IBOutlet UIButton *logoutBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UIScrollView *scrollBackground;
@property (strong, nonatomic) IBOutletCollection(UIButton) NSArray *modeBtn;
@property (strong, nonatomic) IBOutletCollection(UIButton) NSArray *languageBtn;
@property (strong, nonatomic) IBOutletCollection(UIButton) NSArray *fontSizeBtn;
@property (weak, nonatomic) IBOutlet UIButton *soundBtn;
@property (weak, nonatomic) IBOutlet UIButton *gpsTimeBtn;
@property (weak, nonatomic) IBOutlet UIButton *autoUpdateBtn;

- (IBAction)modeBtnClick:(id)sender;
- (IBAction)languageBtnClick:(id)sender;
- (IBAction)fontSizeBtnClick:(id)sender;
- (IBAction)soundBtnClick:(id)sender;
- (IBAction)gpsTimeBtnClick:(id)sender;
- (IBAction)autoUpdateBtnClick:(id)sender;
- (IBAction)showUserGuideBtnClick:(id)sender;
- (IBAction)showAboutAppBtnClick:(id)sender;
- (IBAction)clearFamilyDataBtnClick:(id)sender;
- (IBAction)clearRankingDataBtnClick:(id)sender;
- (IBAction)accessibilityStatementBtnClick:(id)sender;
- (IBAction)logoutBtnClick:(id)sender;

@end

//
//  GameEndViewController.h
//  dementia
//
//

#import <Social/Social.h>

#import <MessageUI/MFMailComposeViewController.h>

#import "BaseViewController.h"

@interface GameEndViewController : BaseViewController <MFMailComposeViewControllerDelegate>

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *congratulationLbl;
@property (weak, nonatomic) IBOutlet UILabel *questionNumberMark;
@property (weak, nonatomic) IBOutlet UILabel *difficultyMark;
@property (weak, nonatomic) IBOutlet UILabel *scoreMark;
@property (weak, nonatomic) IBOutlet UILabel *rankingMark;
@property (weak, nonatomic) IBOutlet UIButton *shareToFacebookBtn;
@property (weak, nonatomic) IBOutlet UIButton *shareToEmailBtn;
@property (weak, nonatomic) IBOutlet UIButton *retryBtn;
@property (weak, nonatomic) IBOutlet UIButton *rankingBtn;

/* Variable */
@property (weak, nonatomic) IBOutlet UILabel *questionNumberLbl;
@property (weak, nonatomic) IBOutlet UILabel *difficultyLbl;
@property (weak, nonatomic) IBOutlet UILabel *scoreLbl;
@property (weak, nonatomic) IBOutlet UILabel *rankingLbl;

@property (strong, nonatomic) NSString *pageTitle;
@property (assign, nonatomic) NSUInteger type;
@property (assign, nonatomic) NSUInteger questionNumber;
@property (assign, nonatomic) NSUInteger difficulty;
@property (assign, nonatomic) NSUInteger score;

@property (copy, nonatomic) void(^retryBlock)(BOOL status);

+ (void)submitMark:(int)gameType gameLevel:(unsigned long)gameLevel marks:(unsigned long)marks correctNo:(unsigned long)correctNo;
- (IBAction)shareToFacebookBtnClick:(id)sender;
- (IBAction)shareToEmailBtnClick:(id)sender;
- (IBAction)retryBtnClick:(id)sender;
- (IBAction)rankingBtnClick:(id)sender;

@end

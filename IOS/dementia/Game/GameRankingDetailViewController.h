//
//  GameRankingDetailViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface GameRankingDetailViewController : BaseViewController <UITableViewDataSource, UITableViewDelegate>

/* For Localization - Last Time View */
@property (weak, nonatomic) IBOutlet UILabel *rankingMark;
@property (weak, nonatomic) IBOutlet UILabel *lastFinishMark1;
@property (weak, nonatomic) IBOutlet UILabel *lastFinishMark2;

/* For Localization - This Time View */
@property (weak, nonatomic) IBOutlet UILabel *questionNumberMark;
@property (weak, nonatomic) IBOutlet UILabel *difficultyMark;
@property (weak, nonatomic) IBOutlet UILabel *scoreMark;
@property (weak, nonatomic) IBOutlet UILabel *thisTimeRankingMark;

/* Variable - Last Time View */
@property (weak, nonatomic) IBOutlet UIView *lastTimeView;
@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;
@property (weak, nonatomic) IBOutlet UILabel *lastFinishLbl;
@property (weak, nonatomic) IBOutlet UILabel *scoreLbl;

/* Variable - This Time View */
@property (weak, nonatomic) IBOutlet UIView *thisTimeView;
@property (weak, nonatomic) IBOutlet UILabel *questionNumberLbl;
@property (weak, nonatomic) IBOutlet UILabel *difficultyLbl;
@property (weak, nonatomic) IBOutlet UILabel *thisTimeScoreLbl;
@property (weak, nonatomic) IBOutlet UILabel *rankingLbl;

@property (weak, nonatomic) IBOutlet UITableView *rankingTableView;

@property (strong, nonatomic) NSString *displayType;

@property (strong, nonatomic) NSString *gameType;
@property (assign, nonatomic) NSUInteger questionNumber;
@property (assign, nonatomic) NSUInteger difficulty;
@property (assign, nonatomic) NSUInteger score;
@property (assign, nonatomic) NSUInteger ranking;

@end

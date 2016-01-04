//
//  GameRankingViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface GameRankingViewController : BaseViewController

@property (weak, nonatomic) IBOutlet UILabel *pageTitleLbl;

@property (strong, nonatomic) IBOutletCollection(UIButton) NSArray *gameRankingBtn;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *scoreLbl;

- (IBAction)gameRankingBtnClick:(id)sender;

@end

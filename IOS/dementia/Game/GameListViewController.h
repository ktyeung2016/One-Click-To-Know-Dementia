//
//  GameListViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface GameListViewController : BaseViewController <UITableViewDataSource, UITableViewDelegate, UIScrollViewDelegate>

@property (weak, nonatomic) IBOutlet UITableView *buttonTableView;
@property (weak, nonatomic) IBOutlet UIButton *resetBtn;
@property (weak, nonatomic) IBOutlet UIButton *rankingBtn;

- (IBAction)resetBtnClick:(id)sender;
- (IBAction)rankingBtnClick:(id)sender;

@end

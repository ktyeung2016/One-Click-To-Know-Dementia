//
//  KnowledgeListViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface KnowledgeListViewController : BaseViewController <UITableViewDataSource, UITableViewDelegate, UIAlertViewDelegate>

@property (weak, nonatomic) IBOutlet UITableView *knowledgeTableView;

@end

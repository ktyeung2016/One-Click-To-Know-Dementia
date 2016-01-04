//
//  DrawerViewController.h
//  dementia
//
//

#import <UIKit/UIKit.h>

@interface DrawerViewController : UIViewController <UITableViewDataSource, UITableViewDelegate>

@property (weak, nonatomic) IBOutlet UITableView *buttonTableView;

@end

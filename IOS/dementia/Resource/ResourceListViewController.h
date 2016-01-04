//
//  ResourceListViewController.h
//  dementia
//
//

#import "BaseViewController.h"

#import "STCollapseTableView.h"

@interface ResourceListViewController : BaseViewController <UITableViewDataSource, UITableViewDelegate, UIAlertViewDelegate>

/* For Localization */
@property (weak, nonatomic) IBOutlet UILabel *districtMark;
@property (weak, nonatomic) IBOutlet UILabel *typeMark;

/* Variable */
@property (weak, nonatomic) IBOutlet UILabel *districtLbl;
@property (weak, nonatomic) IBOutlet UIButton *btnDistrict;
@property (weak, nonatomic) IBOutlet UILabel *typeLbl;
@property (weak, nonatomic) IBOutlet UIButton *btnType;
@property (weak, nonatomic) IBOutlet STCollapseTableView *resourcesTableView;

- (IBAction)districtBtnClick:(id)sender;
- (IBAction)typeBtnClick:(id)sender;

@end

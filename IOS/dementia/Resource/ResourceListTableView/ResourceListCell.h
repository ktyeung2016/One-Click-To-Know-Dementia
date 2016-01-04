//
//  ResourceListCell.h
//  dementia
//
//

#import <UIKit/UIKit.h>

#import "ResourceListData.h"

@interface ResourceListCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *titleLbl;
@property (weak, nonatomic) IBOutlet UIImageView *btnResourceItem;

- (void)setUpViewWithData:(ResourceListData *)data;

@end

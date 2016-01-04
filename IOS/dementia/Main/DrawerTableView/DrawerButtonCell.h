//
//  DrawerButtonCell.h
//  dementia
//
//

#import <UIKit/UIKit.h>

#import "DrawerData.h"

@interface DrawerButtonCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIImageView *backgroundImgView;
@property (weak, nonatomic) IBOutlet UILabel *itemLbl;

- (void)setUpViewWithData:(DrawerData *)data;

@end

//
//  ContactCell.h
//  dementia
//
//

#import <UIKit/UIKit.h>

#import "ContactData.h"

@interface ContactCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIButton *editMemberBtn;
@property (weak, nonatomic) IBOutlet UIImageView *photoImgView;
@property (weak, nonatomic) IBOutlet UILabel *memberNameLbl;
@property (weak, nonatomic) IBOutlet UIButton *callMemberBtn;

- (void)setUpViewWithData:(ContactData *)data;

@end

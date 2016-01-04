//
//  ResourceRemarkCell.h
//  dementia
//
//

#import <UIKit/UIKit.h>

@interface ResourceRemarkCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UILabel *remarkLbl;
@property (weak, nonatomic) IBOutlet UILabel *targetImageIntroductionLbl;
@property (weak, nonatomic) IBOutlet UILabel *lightLbl;
@property (weak, nonatomic) IBOutlet UILabel *earlyLbl;
@property (weak, nonatomic) IBOutlet UILabel *middleLbl;
@property (weak, nonatomic) IBOutlet UILabel *laterLbl;
@property (weak, nonatomic) IBOutlet UILabel *familyLbl;
@property (weak, nonatomic) IBOutlet UILabel *caregiversLbl;

@end

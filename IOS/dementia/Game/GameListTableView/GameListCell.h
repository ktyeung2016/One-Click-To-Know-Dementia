//
//  GameListCell.h
//  dementia
//
//

#import <UIKit/UIKit.h>

@interface GameListCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIImageView *backgroundImgView;
@property (weak, nonatomic) IBOutlet UILabel *gameNameLbl;

- (void)setUpViewWithImageName:(NSString *)imageName gameName:(NSString *)gameName;

@end

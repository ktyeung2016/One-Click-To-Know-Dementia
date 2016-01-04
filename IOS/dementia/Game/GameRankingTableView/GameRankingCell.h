//
//  GameRankingCell.h
//  dementia
//
//

#import <UIKit/UIKit.h>

#import "GameRankingData.h"

@interface GameRankingCell : UITableViewCell

@property (weak, nonatomic) IBOutlet UIView *rankingView;
@property (weak, nonatomic) IBOutlet UIImageView *markerImgView;
@property (weak, nonatomic) IBOutlet UILabel *rankingLbl;
@property (weak, nonatomic) IBOutlet UILabel *scoreLbl;
@property (weak, nonatomic) IBOutlet UILabel *dateLbl;

- (void)setUpViewWithData:(GameRankingData *)data;

@end

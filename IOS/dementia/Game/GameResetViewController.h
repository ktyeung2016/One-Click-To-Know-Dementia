//
//  GameResetViewController.h
//  dementia
//
//

#import "BaseViewController.h"

@interface GameResetViewController : BaseViewController

@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *gameNameLbl;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *difficultyMark;
@property (strong, nonatomic) IBOutletCollection(UILabel) NSArray *difficultyLbl;
@property (strong, nonatomic) IBOutletCollection(UIButton) NSArray *resetBtn;

- (IBAction)resetBtnClick:(id)sender;

@end

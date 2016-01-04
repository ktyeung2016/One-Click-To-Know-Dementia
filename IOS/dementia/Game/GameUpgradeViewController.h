//
//  GameUpgradeViewController.h
//  dementia
//
//

#import <UIKit/UIKit.h>

@interface GameUpgradeViewController : UIViewController

@property (weak, nonatomic) IBOutlet UILabel *congratulationLbl;
@property (weak, nonatomic) IBOutlet UILabel *remarkLbl;
@property (weak, nonatomic) IBOutlet UIButton *confirmBtn;

@property (assign, nonatomic) NSUInteger type;
@property (assign, nonatomic) NSUInteger difficulty;

@property (copy, nonatomic) void(^confirmBlock)(BOOL status);

- (IBAction)confirmBtnClick:(id)sender;

@end

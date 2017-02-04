//
//  LTCConcernTableViewCell.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LTCConcern+CoreDataClass.h"

@interface LTCConcernTableViewCell : UITableViewCell
@property (readonly, nonatomic, strong) LTCConcern *concern;
- (void)configureWithConcern:(LTCConcern *)concern;
@end

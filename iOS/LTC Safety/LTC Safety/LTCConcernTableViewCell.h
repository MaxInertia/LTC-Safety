//
//  LTCConcernTableViewCell.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "LTCConcern+CoreDataClass.h"

/**
 The LTCConcernTableViewCell class is used for displaying individual concern cells within the LTCConcernViewController. For every concern in the concern view model there is a single cell that displays a brief summary of the data related to that concern.
 */
@interface LTCConcernTableViewCell : UITableViewCell

/**
 The concern who's information is being displayed in the cell. This can only be set through the -configureWithConcern: method.
 */
@property (readonly, nonatomic, strong) LTCConcern *concern;

/**
 Updates the cell's contents to display the information for a new concern. This causes the cell's concern property to update.

 @pre The concern must be non-nil
 @param concern The concern who's contents should be displayed within the cell.
 @post The cell's contents has been updated to display the information for the passed cell.
 */
- (void)configureWithConcern:(LTCConcern *)concern;
@end

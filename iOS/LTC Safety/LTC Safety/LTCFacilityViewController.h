//
//  LTCFacilityViewController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import "LTCValueSelectionController.h"

/**
 The LTCFacilityViewController class is used to provide value selection for carehome facilities. A subclass of LTCValueSelectionController is used due to limitations with the third-party XLForms library. Because only the class can be passed, it is not possible to pass a filename in the designated initializer. As a result, the fileName must be provided by subclassing LTCValueSelectionController.
 */
@interface LTCFacilityViewController : LTCValueSelectionController

@end

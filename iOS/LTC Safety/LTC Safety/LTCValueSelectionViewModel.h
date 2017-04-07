//
//  LTCValueSelectionViewModel.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-03.
//  Copyright © 2017 CS371 Group 2. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "LTCRowValue.h"

/**
 The LTCValueSelectionViewModel class is used to provide the data necessary for the LTCValueSelectionViewController class to display the value options to the user. This class loads the value selection config from file and creates row descriptors that are used by the value selection view controller.
 
 History properties: Instances of this class should not vary from the time they are created.
 
 Invariance properties: This class assumes that the title and otherPrompt are non-nil and that the rowValues array is non-nil and contains only valid row value classes.
 
 */
@interface LTCValueSelectionViewModel : NSObject

/**
 The title for the value selection model which is shown in the view controller's navigation bar.
 */
@property (readonly, nonatomic, strong) NSString *title;

/**
 The prompt shown in the value selection view controller's custom input alert view to prompt the user for input.
 */
@property (readonly, nonatomic, strong) NSString *otherPrompt;

/**
 The list of pre-defined options to be displayed in the value selection view controller.
 */
@property (readonly, nonatomic, strong) NSArray<LTCRowValue *> *rowValues;

/**
 The designated constructor for constructing a view model from a configuration file.

 @pre The file name refers to a property list file in the application bundle with a title, other prompt, and list of items.
 @attention The file name must be the name for a plist. The extension should not be included with the file name.
 @param fileName The name of the configuration file within the applicaiton bundle.
 @return An LTCValueSelectionViewModel object initialized using the configuration file.
 */
+ (instancetype)selectionViewModelForFileName:(NSString *)fileName;
@end

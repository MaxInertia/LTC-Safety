//
//  LTCValueSelectionController.h
//  LTC Safety
//
//  Created by Allan Kerr on 2017-02-02.
//  Copyright © 2017 Allan Kerr. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <XLForm/XLForm.h>

/**
 The LTCValueSelectionController class is used for providing the client with a set of predefined options to choose from when filling in data in the new concern view controller.
 @attention For all intents and purposes this class is abstract and should not instantiated. It must be subclasses so that -fileName can be overriden.
 
 History/Invariance properties: This class is abstact and should not be instantiated so it has no history or invarience properties.
  
 */
@interface LTCValueSelectionController : XLFormViewController <XLFormRowDescriptorViewController>

/**
 The file name for the properties file that the value options are loaded from.
 
 @attention This must be overriden by subclasses to provide the filename the options should be loaded from.
 @return The file name for the properties file that the category options are loaded from.
 */
@property (readonly, nonatomic, copy) NSString *fileName;

/**
 The boolean value for if this view controller should display an "other" prompt to the user.
 */
@property (readonly, nonatomic) BOOL hasOther;

/**
 The row descriptor required by XLForms for passing the selected value back to the parent XLFormViewController.
 */
@property (nonatomic) XLFormRowDescriptor *rowDescriptor;
@end
